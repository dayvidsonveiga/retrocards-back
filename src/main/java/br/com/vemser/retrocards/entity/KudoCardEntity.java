package br.com.vemser.retrocards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "kudo_cards")
public class KudoCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kudocard_seq")
    @SequenceGenerator(name = "kudocard_seq", sequenceName = "seq_kudocard", allocationSize = 1)
    @Column(name = "id_kudo_card")
    private Integer idKudoCard;

    @Column(name = "id_creator")
    private Integer idCreator;

    @Column(name = "title")
    private String title;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kudo_box", referencedColumnName = "id_kudo_box")
    private KudoBoxEntity kudobox;
}
