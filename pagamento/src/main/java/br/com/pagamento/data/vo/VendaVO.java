package br.com.pagamento.data.vo;

import br.com.pagamento.entity.Venda;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonPropertyOrder({"id", "data", "produtos", "valorTotal"})
@Data
@EqualsAndHashCode(callSuper = false)
public class VendaVO extends RepresentationModel<VendaVO> implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("data")
    private Date data;

    @JsonProperty("produtos")
    private List<ProdutoVendaVO> produtos;

    @JsonProperty("valorTotal")
    private Double valorTotal;

    public static VendaVO create(Venda venda) {
        return new ModelMapper().map(venda, VendaVO.class);
    }

}
