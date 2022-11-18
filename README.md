# PrintecTestApp

Stack of technologies: 
1. Retrofit (Netrowk)
2. Hilt (di)
3. Datastore (cache)
4. Compose (ui)
5. Navigation Component (navigation)

Project sctucture: 
MVVM
Since there's no difficult business logic, no "interactor/use cases" were used.
CloudDataSource:
- return cloud data or proper domain exception
Repository:
- return mapped cloudToDomain data or ui error depends on domain exception
ViewModel:
- communicates with repository directly. 
- handle returned data from repository.
- handle ui input validations. (Sales screen)
