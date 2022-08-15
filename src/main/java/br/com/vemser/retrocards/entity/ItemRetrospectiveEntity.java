package br.com.vemser.retrocards.entity;

import br.com.vemser.retrocards.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name = "item_retrospectivas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class ItemRetrospectiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemretrospectiva_seq")
    @SequenceGenerator(name = "itemretrospectiva_seq", sequenceName = "seq_item_retrospectiva", allocationSize = 1)
    @Column(name = "id_item_retrospective")
    private Integer idItemRetrospective;

    @Column(name = "item_type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_retrospective", referencedColumnName = "id_retrospective")
    private RetrospectiveEntity retrospective;
}
