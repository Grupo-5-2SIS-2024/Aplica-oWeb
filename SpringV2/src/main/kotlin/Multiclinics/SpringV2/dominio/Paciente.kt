package Multiclinics.SpringV2.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF
import java.sql.Time
import java.time.LocalDate
import java.util.*

@Entity
data class Paciente(
   @field:Id
   @field:GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Int?,

   @field: Size(min = 3)
   var nome: String?,

   @field: Size(min = 3)
   var sobrenome: String?,

   @field:Email(message = "O email fornecido não é válido.")
   var email: String?,

   @field:NotBlank(message = "O CPF não pode estar em branco.")
   @field:CPF(message = "O CPF fornecido não é válido.")
   var cpf: String?,

   @field:NotBlank(message = "O genero não pode estar em branco.")
   var Genero: String?,

   @field:NotBlank(message = "O telefone não pode estar em branco.")
   var telefone: String?,

   @field:OneToOne
   var responsavel: Responsavel,

   var dataNascimento: LocalDate?,

   @field: OneToOne
   var endereco: Endereco



)
