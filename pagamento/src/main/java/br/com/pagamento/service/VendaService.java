package br.com.pagamento.service;

import br.com.pagamento.data.vo.VendaVO;
import br.com.pagamento.entity.ProdutoVenda;
import br.com.pagamento.entity.Venda;
import br.com.pagamento.exception.ResourceNotFoundException;
import br.com.pagamento.repository.ProdutoVendaRepository;
import br.com.pagamento.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public VendaService (VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository){
        this.vendaRepository = vendaRepository;
        this.produtoVendaRepository = produtoVendaRepository;
    }

    public VendaVO create(VendaVO vendaVO) {
        Venda venda = vendaRepository.save(Venda.create(vendaVO));
        List<ProdutoVenda> listProdutoVenda = new ArrayList<>();
        vendaVO.getProdutos().forEach(p -> {
            ProdutoVenda pv = ProdutoVenda.create(p);
            pv.setVenda(venda);
            listProdutoVenda.add(produtoVendaRepository.save(pv));
        });
        venda.setProdutos(listProdutoVenda);
        return VendaVO.create(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        var page = vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaVO);
    }

    public VendaVO findById(Long id) {
        var entity = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return VendaVO.create(entity);
    }

    private VendaVO convertToVendaVO(Venda venda) {
        return VendaVO.create(venda);
    }


}
