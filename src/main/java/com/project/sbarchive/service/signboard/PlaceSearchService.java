package com.project.sbarchive.service.signboard;

import com.google.gson.*;
import com.project.sbarchive.dto.signboard.SearchResultDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class PlaceSearchService {

    private Gson gson;

    @PostConstruct
    private void setup()
    {
        gson=new Gson();
    }

    public ArrayList<SearchResultDTO> searchPlace(String keyword){
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding fail!",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query="+keyword+"&display=5&start=1&sort=random";    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", "7WWXLnKWEYUkpYL8L63y");
        requestHeaders.put("X-Naver-Client-Secret", "8hHyotLFOj");
        String responseBody = get(apiURL,requestHeaders);

        System.out.println("네이버에서 받은 결과 = " + responseBody);
        System.out.println("-----------------------------------------");

        responseBody = "{ \"items\":[" + ( (responseBody.split("\\[")[1]).split("]}")[0].trim()  ) + "] }";
        log.info(responseBody);

        JsonParser parser = new JsonParser();

        Object obj = parser.parse(responseBody); // 기존 검색 결과값을 오브젝트에 담고
        JsonObject jobj = (JsonObject) parser.parse(responseBody); // 그 결과값을 JSON 형태로 파싱

        JsonArray jsonArray = (JsonArray) jobj.get("items"); // JSONObject를 배열 형태료 변환

        ArrayList<SearchResultDTO> searchResultDTOS = new ArrayList<>();

        for (int i=0; i<jsonArray.size(); i++){
            StringBuffer sb = new StringBuffer();
            // API를 이용해 받아온 JSON 데이터를 DTO에 옮겨 담는다
            SearchResultDTO searchResultDTO = gson.fromJson(jsonArray.get(i), SearchResultDTO.class);

            // 기존 좌표 = 1269548283 => sb를 이용해 .을 추가 => 126.9548283
            sb.append((jsonArray.get(i).toString().split("mapx\":\"")[1]).split("\",")[0]);
            sb.insert(3, ".");
            searchResultDTO.setXOffSet(
                    sb.toString()
            );

            // 기존 좌표 ex) = 375476566 => sb를 이용해 .을 추가 => 37.5476566
            sb = new StringBuffer();
            sb.append((jsonArray.get(i).toString().split("mapy\":\"")[1]).split("\"")[0]);
            sb.insert(2, ".");
            searchResultDTO.setYOffSet(sb.toString());
            searchResultDTOS.add(searchResultDTO);
        }

        for(SearchResultDTO a : searchResultDTOS) {
            log.info(a);
        }


        return searchResultDTOS;
    }

    // 요청을 보내고 결과값을 받아오는 함수
    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    // GET 방식으로 URL에 접근 한 뒤 정상적으로 접근했는지 체크해서 결과값을 반환해주는 메소드
    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    //  요청받은 Body를 읽는 readBody함수
    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (
                BufferedReader lineReader = new BufferedReader(streamReader)
        ) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }


}
