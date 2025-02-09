package com.towertex.erstemodel

import com.towertex.ersteapi.services.TransparentAccountsApiContract
import com.towertex.erstemodel.repository.TransparentAccountsRepository
import com.towertex.erstemodel.repository.TransparentAccountsRepositoryContract
import com.towertex.erstemodel.repository.TransparentTransactionsRepository
import com.towertex.erstemodel.repository.TransparentTransactionsRepositoryContract
import com.towertex.erstemodel.room.ErsteDatabase

//data model is divided into sections
//each section is implemented via dedicated delegate
//data model is using SingleSourceOfTruth architecture where the app gets only Room objects but never the Retrofit objects
//in the Demo app there are only 2 parts of the model and it has 4 services
class ErsteRepository(
    transparentAccountsApi: TransparentAccountsApiContract,
    db: ErsteDatabase,
):
    TransparentAccountsRepositoryContract by TransparentAccountsRepository(transparentAccountsApi, db.ersteDao),
    TransparentTransactionsRepositoryContract by TransparentTransactionsRepository(transparentAccountsApi, db.ersteDao)