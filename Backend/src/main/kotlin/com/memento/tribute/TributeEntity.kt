package com.memento.tribute

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "tribute")
data class TributeEntity(
    @Column
    val personId: Int,

    @Column
    val attachmentUrl: String,

    @Column
    val description: String,

    @Column
    val name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null
)
