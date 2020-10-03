package com.memento

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
class TributeController {
    @Autowired
    lateinit var tributeRepository: TributeRepository

    @GetMapping("/tribute")
    fun getTributeByPersonId(@RequestParam("personId") personId: Int): ResponseEntity<List<TributeEntity>> {
        val tributes = tributeRepository.findByPersonId(personId)

        return ResponseEntity(tributes, HttpStatus.OK)
    }

    @PostMapping("/tribute")
    fun saveTribute(@RequestBody @Valid tribute: TributeEntity): ResponseEntity<TributeEntity> {
        tribute.id = null
        tributeRepository.save(tribute)

        return ResponseEntity(HttpStatus.OK)
    }
}

interface TributeRepository : JpaRepository<TributeEntity, Int> {
    fun findByPersonId(personId: Int): List<TributeEntity>
}

@Entity
data class TributeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column
    @NotNull
    var personId: Int? = null,

    @Column
    @NotNull
    var attachmentUrl: String? = null,

    @Column
    @NotNull
    var description: String? = null,

    @Column
    @NotNull
    var name: String? = null
)
