package kz.izzi.bus.service;

import kz.izzi.bus.domain.LotteryRound;
import kz.izzi.bus.domain.Ticket;
import kz.izzi.bus.repository.LotteryRoundRepository;
import kz.izzi.bus.repository.TicketRepository;
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

    public LotteryRoundScheduler(TicketRepository ticketRepository, LotteryRoundRepository lotteryRoundRepository) {
        this.ticketRepository = ticketRepository;
        this.lotteryRoundRepository = lotteryRoundRepository;
    }


    @Scheduled(cron = "0 0 * * * *")
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
        int winId = new Random().nextInt(validTickets.size() + 1);
        Ticket winner = validTickets.get(winId);
        log.info("Winning ticket {}", winner);
        winner.setValid(false);
        ticketRepository.save(winner);
        LotteryRound round = new LotteryRound();
        round.setDatetime(LocalDateTime.now().atZone(ZoneId.systemDefault()));
        round.setWinner(winner);
        LotteryRound saved = lotteryRoundRepository.save(round);
        log.info("Round {} saved", saved);
    }



}
