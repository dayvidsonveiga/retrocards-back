package br.com.vemser.retrocards.entity;

import br.com.vemser.retrocards.controller.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item_retrospectivas")
public class ItemRetrospectiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemretrospectiva_seq")
    @SequenceGenerator(name = "itemretrospectiva_seq", sequenceName = "seq_item_retrospectiva", allocationSize = 1)
    @Column(name = "id_item_retrospective")
    private Integer idItemRetrospective;

    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_retrospective", referencedColumnName = "id_retrospective")
    private RetrospectiveEntity retrospective;
}
