package kz.izzi.bus.service;

import kz.izzi.bus.domain.LotteryRound;
import kz.izzi.bus.domain.Ticket;
import kz.izzi.bus.repository.LotteryRoundRepository;
import kz.izzi.bus.repository.TicketRepository;
import kz.izzi.bus.service.dto.sms.SmsRequest;
import kz.izzi.bus.service.dto.sms.SmsResponse;
import kz.izzi.bus.service.util.SmsTextFormer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

/**
 * Created by Vlad on 05.08.2017.
 */
@Service
public class LotteryRoundScheduler {


    public static final Logger log = LoggerFactory.getLogger(LotteryRoundScheduler.class);
    private static final LocalTime START_TIME = LocalTime.of(10, 0);
    private static final LocalTime END_TIME = LocalTime.of(21, 0);

    private final TicketRepository ticketRepository;
    private final LotteryRoundRepository lotteryRoundRepository;
    private final SmsService smsService;

    public LotteryRoundScheduler(TicketRepository ticketRepository, LotteryRoundRepository lotteryRoundRepository, SmsService smsService) {
        this.ticketRepository = ticketRepository;
        this.lotteryRoundRepository = lotteryRoundRepository;
        this.smsService = smsService;
    }


    @Scheduled(cron = "0 0/15 * * * *")
    public void checkTime() {
        LocalTime time = LocalTime.now();
        if (time.isAfter(START_TIME) && time.isBefore(END_TIME)) {
            makeSomeoneHappy();
        }
    }

    private void makeSomeoneHappy() {
        List<Ticket> validTickets = ticketRepository.findByValid(true);
        if (validTickets.isEmpty()) {
            log.info("No tickets were registered for this round");
            return;
        }
        int winId = new Random().nextInt(validTickets.size());
        Ticket winner = validTickets.get(winId);
        log.info("Winning ticket {}", winner);
        winner.setValid(false);
        ticketRepository.save(winner);
        LotteryRound round = new LotteryRound();
        round.setDatetime(LocalDateTime.now().atZone(ZoneId.systemDefault()));
        round.setWinner(winner);
        LotteryRound saved = lotteryRoundRepository.save(round);
        log.info("Round {} saved", saved);
        SmsResponse smsResponse =  smsService.sendMessage(new SmsRequest(winner.getPhoneNumber(), SmsTextFormer.getWinningText()));
        if (smsResponse.isError()) {
            log.error("Ошибка при отправке СМС сообщения победителю {}", winner);
        }


    }



}
