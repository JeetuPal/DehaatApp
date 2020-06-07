package com.dehaat.assignment.repository.account

import com.dehaat.assignment.api.main.OpenApiMainService
import com.dehaat.assignment.persistence.AccountPropertiesDao
import com.dehaat.assignment.session.SessionManager
import javax.inject.Inject

class AccountRepository
@Inject
constructor(
  val openApiMainService: OpenApiMainService,
  val accountPropertiesDao: AccountPropertiesDao,
  val sessionManager: SessionManager)
{

}