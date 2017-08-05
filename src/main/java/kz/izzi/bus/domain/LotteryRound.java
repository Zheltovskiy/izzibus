package kz.izzi.bus.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LotteryRound.
 */
@Entity
@Table(name = "lottery_round")
public class LotteryRound implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private ZonedDateTime datetime;

    @OneToOne
    @JoinColumn(unique = true)
    private Ticket winner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public LotteryRound datetime(ZonedDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public Ticket getWinner() {
        return winner;
    }

    public LotteryRound winner(Ticket ticket) {
        this.winner = ticket;
        return this;
    }

    public void setWinner(Ticket ticket) {
        this.winner = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LotteryRound lotteryRound = (LotteryRound) o;
        if (lotteryRound.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lotteryRound.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LotteryRound{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
