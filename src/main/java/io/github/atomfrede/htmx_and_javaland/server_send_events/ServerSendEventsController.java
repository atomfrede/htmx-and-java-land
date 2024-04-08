package io.github.atomfrede.htmx_and_javaland.server_send_events;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTrigger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import social.bigbone.MastodonClient;
import social.bigbone.api.Pageable;
import social.bigbone.api.entity.Status;
import social.bigbone.api.exception.BigBoneRequestException;

import java.io.IOException;

import static social.bigbone.api.method.TimelineMethods.StatusOrigin.LOCAL_AND_REMOTE;

@Controller
public class ServerSendEventsController {

    final MastodonClient client = new MastodonClient.Builder("mastodon.social").accessToken(System.getenv("ACCESS_TOKEN")).build();


    @GetMapping("/server-send-events")
    public String index() {
        return "server-send-events/index";
    }
    @GetMapping("/server-send-events/sse-emitter")
    public SseEmitter progressEvents() throws BigBoneRequestException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        emit(sseEmitter);

        return sseEmitter;
    }

    @Async
    void emit(SseEmitter emitter) throws BigBoneRequestException {
        final Pageable<Status> statuses = client.timelines()
                .getTagTimeline("javaland", LOCAL_AND_REMOTE).execute();

        statuses.getPart().forEach(it -> {

            try {
                emitter.send(it.getContent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
