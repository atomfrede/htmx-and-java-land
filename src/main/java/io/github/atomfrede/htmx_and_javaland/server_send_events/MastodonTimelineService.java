package io.github.atomfrede.htmx_and_javaland.server_send_events;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import social.bigbone.MastodonClient;
import social.bigbone.api.Pageable;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;

import java.io.IOException;
import java.util.List;

import static social.bigbone.api.method.TimelineMethods.StatusOrigin.LOCAL_AND_REMOTE;

@Service
public class MastodonTimelineService {

    final MastodonClient client = new MastodonClient.Builder("mastodon.social").accessToken(System.getenv("ACCESS_TOKEN")).build();

    private List<SseEmitter> sseEmitters;
    public MastodonTimelineService() throws IOException, InterruptedException, BigBoneRequestException {

//        client.streaming().hashtag("javaland", false, System.out::println);


        final Pageable<Status> statuses = client.timelines().getTagTimeline("javaland", LOCAL_AND_REMOTE).execute();
    }


    public void listenAndEmit() {
        while (true) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }




}
