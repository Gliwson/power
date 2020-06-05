package pl.power.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.power.domain.xmlDomain.RemitUMM;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ImportDataXmlService {

    private static final String url = "https://zepak.com.pl/rss/rss.php";

    public List<RemitUMM> getLinkToList() {
        ResponseEntity<String> respEntity = getStringResponseEntity();

        Scanner scanner = new Scanner(respEntity.toString());
        List<String> listLink = new ArrayList<>();
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            Scanner scanner1 = new Scanner(s);
            String inLine1 = scanner1.findInLine("\"(.*)\"");
            if (inLine1 != null) {
                String replace = inLine1.replace("\"", "");
                listLink.add(replace);
            }
        }

        List<String> stringList = listLink.stream()
                .filter(s -> s.startsWith("http://")).collect(Collectors.toList());
        List<String> openLinks = openLink(stringList);
        return xmlMapper(openLinks);

    }

    private static ResponseEntity<String> getStringResponseEntity() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.TEXT_XML);
        headers.add("user-agent", "Mozilla/5.0 Firefox/26.0");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private List<String> openLink(List<String> list) {
        List<String> openLinkString = new ArrayList<>();
        RestTemplate builder = getRestTemplate();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        list.forEach(s -> executorService.execute(() -> {
            String body = builder.getForEntity(s, String.class).getBody();
            openLinkString.add(body);
        }));
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.yield();
        }
        return openLinkString;
    }

    private static RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2))
                .messageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .build();
    }

    private List<RemitUMM> xmlMapper(List<String> list) {
        List<RemitUMM> remitUMMList = new ArrayList<>();
        for (String sss : list) {
            XmlMapper xmlMapper = new XmlMapper();

            RemitUMM result = null;
            try {
                result = xmlMapper.readValue(sss, RemitUMM.class);
            } catch (JsonProcessingException | IllegalArgumentException e) {
                e.printStackTrace();
                continue;

            }
            if (result == null) {
                continue;
            }
            remitUMMList.add(result);
        }
        return remitUMMList;
    }
}
