package br.com.pagamento.config;

import br.com.pagamento.data.vo.ProdutoVO;
import br.com.pagamento.service.ProdutoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProdutoReceiveMessage {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoReceiveMessage(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receiveMessage(@Payload ProdutoVO produtoVO)  {
        produtoService.create(produtoVO);
    }

}
