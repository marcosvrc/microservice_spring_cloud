package br.com.crud.service;

import br.com.crud.data.vo.ProdutoVO;
import br.com.crud.entity.Produto;
import br.com.crud.exception.ResourceNotFoundException;
import br.com.crud.message.ProdutoSendMessage;
import br.com.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoSendMessage produtoSendMessage;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, ProdutoSendMessage produtoSendMessage){
        this.produtoRepository = produtoRepository;
        this.produtoSendMessage = produtoSendMessage;
    }

    public ProdutoVO create(ProdutoVO produtoVO){
        ProdutoVO produtoVOCreated = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
        produtoSendMessage.sendMessage(produtoVOCreated);
        return produtoVOCreated;
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map((this::converteToProdutoVO));
    }

    private ProdutoVO converteToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }

    public ProdutoVO findById(Long id) {
        var entity= produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(ProdutoVO produtoVO) {
        final Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());
        if(!optionalProduto.isPresent()){
            new ResourceNotFoundException("No records found for this id");
        }
        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }

    public void delete(Long id) {
        var entity= produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        produtoRepository.delete(entity);
    }
}
