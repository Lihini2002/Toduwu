package com.example.spotsync.datalayer

import com.example.spotsync.sharedlayer.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

//import kotlinx.coroutines.flow.Flow


interface AuthRepository
{
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
}