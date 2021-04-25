package br.com.pagamento.service;

import br.com.pagamento.data.vo.ProdutoVO;
import br.com.pagamento.entity.Produto;
import br.com.pagamento.exception.ResourceNotFoundException;
import br.com.pagamento.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService (ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public ProdutoVO create(ProdutoVO produtoVO) {
        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    private ProdutoVO findById(Long id) {
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return ProdutoVO.create(entity);
    }

    private ProdutoVO convertToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }

}
