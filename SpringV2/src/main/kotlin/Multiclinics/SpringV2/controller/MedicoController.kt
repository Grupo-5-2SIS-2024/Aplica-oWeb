package Multiclinics.SpringV2.controller

import Multiclinics.SpringV2.Service.MedicoService
import Multiclinics.SpringV2.dominio.Consulta
import Multiclinics.SpringV2.dominio.Medico
import Multiclinics.SpringV2.repository.ConsultaRepository
import Multiclinics.SpringV2.repository.MedicoRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/medicos")
class MedicoController(
    val medicoRepository: MedicoRepository,
    val medicoService: MedicoService
) {

    @PutMapping("/login")
    fun logarMedico(@RequestBody medico: Medico): ResponseEntity<Medico> {
        val medicoLogado = medicoRepository.findByEmailAndSenha(medico.email ?: "", medico.senha ?: "")

        return if (medicoLogado != null) {
            medicoLogado.ativo = true
            medicoRepository.save(medicoLogado) // Atualiza o médico como ativo no repositório
            ResponseEntity.ok(medicoLogado)
        } else {
            ResponseEntity.status(401).build()
        }
    }

    @PutMapping("/logout")
    fun deslogarMedico(@RequestBody medico: Medico): ResponseEntity<Medico> {
        val medicoLogado = medicoRepository.findByEmail(medico.email ?: "")

        return if (medicoLogado != null && medicoLogado.ativo) {
            // Marca o médico como inativo
            medicoLogado.ativo = false
            medicoRepository.save(medicoLogado) // Atualiza o médico como inativo no repositório
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(401).build()
        }
    }



    @PostMapping
    fun adicionarMedico(@RequestBody @Valid novoMedico: Medico): ResponseEntity<Medico> {
        medicoService.salvar(novoMedico)
        return ResponseEntity.status(201).body(novoMedico)
    }

    @PutMapping("/{id}")
    fun atualizarMedico(@PathVariable id: Int, @RequestBody @Valid novoMedico: Medico): ResponseEntity<Medico> {
        val medicoExistente = medicoRepository.findById(id)
        if (medicoExistente.isPresent) {
            var medicoEscolhido = medicoExistente.get()

            val medicoAtualizado = medicoService.atualizar(novoMedico, medicoEscolhido)
            return ResponseEntity.status(200).body(medicoAtualizado)
        } else {
            return ResponseEntity.status(404).build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletarMedico(@PathVariable id: Int): ResponseEntity<Medico> {
        if (medicoRepository.existsById(id)) {
            medicoRepository.deleteById(id)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @GetMapping
    fun listarMedicos(): ResponseEntity<List<Medico>> {
        val medicos = medicoService.getLista()
        return ResponseEntity.status(200).body(medicos)
    }



}