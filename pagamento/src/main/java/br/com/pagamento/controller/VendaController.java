package br.com.pagamento.controller;

import br.com.pagamento.data.vo.VendaVO;
import br.com.pagamento.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaVO> assembler;

    @Autowired
    public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaVO> assembler){
        this.vendaService = vendaService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}" , produces = {"application/json"})
    public VendaVO findById(@PathVariable("id") Long id) {
        VendaVO vendaVO = vendaService.findById(id);
        vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return vendaVO;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "limit", defaultValue = "12") int limit,
                            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable =  PageRequest.of(page,limit, Sort.by(sortDirection,"data"));
        Page<VendaVO> vendas = vendaService.findAll(pageable);
        vendas.stream().forEach(
                v -> v.add(linkTo(methodOn(VendaController.class).findById(v.getId())).withSelfRel())
        );
        PagedModel<EntityModel<VendaVO>> pageModel = assembler.toModel(vendas);
        return new ResponseEntity<>(pageModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public VendaVO create(@RequestBody VendaVO vendaVO) {
        VendaVO vendaVOCreated = vendaService.create(vendaVO);
        vendaVOCreated.add(linkTo(methodOn(VendaController.class).findById(vendaVOCreated.getId())).withSelfRel());
        return vendaVOCreated;
    }


}
