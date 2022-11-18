# PrintecTestApp

Stack of technologies: 
1. Retrofit (Netrowk)
2. Hilt (di)
3. Datastore (cache)
4. Compose (ui)
5. Navigation Component (navigation)

Project sctucture: 
MVVM</br>
(Since there's no difficult business logic, no "interactor/use cases" were used.)
1. CloudDataSource:
     * return cloud data or proper domain exception
2. Repository
     * return mapped cloudToDomain data or ui error depends on domain exception
3. ViewModel:
     * communicates with repository directly. 
     * handle returned data from repository.
     * handle ui input validations. (Sales screen)
