package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteReposity reposity;

    @PostMapping
    @Transactional
    public void cadastrar (@Valid @RequestBody DadosCadastroPaciente dados){
        reposity.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar (@PageableDefault (size = 10, sort = {"nome"}) Pageable paginacao) {
        return reposity.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar (@Valid @RequestBody DadosAtualizarPaciente dados) {
        var paciente = reposity.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping ("/{id}")
    @Transactional
    public void excluir (@PathVariable Long id) {
        var paciente = reposity.getReferenceById(id);
        paciente.excluir();
    }
}
