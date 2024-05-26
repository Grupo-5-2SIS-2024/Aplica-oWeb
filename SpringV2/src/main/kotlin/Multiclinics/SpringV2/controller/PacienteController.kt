package Multiclinics.SpringV2.controller

import Multiclinics.SpringV2.Service.PacienteService
import Multiclinics.SpringV2.dominio.Medico
import Multiclinics.SpringV2.dominio.Paciente
import Multiclinics.SpringV2.repository.PacienteRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pacientes")
class PacienteController(
    val pacienteRepository: PacienteRepository,
    val pacienteService: PacienteService
) {
    @PostMapping
    fun adicionarPaciente(@RequestBody novoPaciente: Paciente): ResponseEntity<Paciente> {
        pacienteService.salvar(novoPaciente)
        return ResponseEntity.status(201).body(novoPaciente)
    }

    @PutMapping("/{id}")
    fun atualizarPaciente(@PathVariable id: Int, @RequestBody @Valid novoPaciente: Paciente): ResponseEntity<Paciente> {
        val PacienteExistente = pacienteRepository.findById(id)
        if (PacienteExistente.isPresent) {
            val PacienteEscolhido = PacienteExistente.get()

            val pacienteAtualizado = pacienteService.atualizar(novoPaciente, PacienteEscolhido)
            return ResponseEntity.status(200).body(pacienteAtualizado)
        } else {
            return ResponseEntity.status(404).build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletarPaciente(@PathVariable id: Int): ResponseEntity<Paciente> {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @GetMapping
    fun listarPaciente(): ResponseEntity<List<Paciente>> {
        val pacientes = pacienteService.getLista()
        return ResponseEntity.status(200).body(pacientes)
    }

}