package br.com.pagamento.service;


import br.com.pagamento.repository.ProdutoVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoVendaService {

    private final ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public ProdutoVendaService (ProdutoVendaRepository produtoVendaRepository){
        this.produtoVendaRepository = produtoVendaRepository;
    }
}
