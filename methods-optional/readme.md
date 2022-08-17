
//    @Operation(summary = "List retrospective items associated with the retrospective")
//    @GetMapping("/list/retrospective/{idRetrospective}")
//    public ResponseEntity<List<ItemRetrospectiveDTO>> listByIdRetrospective(@PathVariable("idRetrospective") Integer idRetrospective) {
//        return new ResponseEntity<>(itemRetrospectiveService.listByIdRetrospective(idRetrospective), HttpStatus.OK);
//    }


//    @Operation(summary = "Update retrospective item")
//    @PutMapping("/update/{idKudocard}")
//    public ResponseEntity<KudoCardDTO> update(@PathVariable("idKudocard") Integer idItem, KudoCardUpdateDTO kudoCardUpdateDTO) throws NegociationRulesException {
//        return new ResponseEntity<>(kudoCardService.update(idItem, kudoCardUpdateDTO), HttpStatus.OK);
//    }

//    @Operation(summary = "Update retrospective")
//    @PutMapping("/update/{idRetrospective}")
//    public ResponseEntity<RetrospectiveDTO> update(@PathVariable("idRetrospective") Integer idRetrospective,
//                                         RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
//        return new ResponseEntity<>(retrospectiveService.update(idRetrospective, retrospectiveCreateDTO), HttpStatus.OK);
//    }

//    @Operation(summary = "Remove retrospective and all data")
//    @DeleteMapping("/delete/{idRetrospective}")
//    public void delete(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException {
//        retrospectiveService.delete(idRetrospective);
//    }

//    @Operation(summary = "List all retrospective")
//    @GetMapping("/list")
//    public ResponseEntity<List<RetrospectiveDTO>> listAll() {
//        return new ResponseEntity<>(retrospectiveService.listAll(), HttpStatus.OK);
//    }

//    @Operation(summary = "Register new admin")
//    @PostMapping("/create-admin")
//    public ResponseEntity<UserDTO> createAdmin(@RequestBody @Valid UserCreateDTO userCreateDTO) throws NegociationRulesException {
//        return new ResponseEntity<>(userService.registerAdmin(userCreateDTO), HttpStatus.CREATED);
//    }

    @Operation(summary = "List retrospective items associated with the retrospective")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucess! Returns all Items referring to ID retrospective."),
                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
            }
    )
    @GetMapping("/list/retrospective/{idRetrospective}")
    ResponseEntity<List<ItemRetrospectiveDTO>> listByIdRetrospective(@PathVariable("idRetrospective") Integer idRetrospective);

@Operation(summary = "Register new admin")
@ApiResponses(
value = {
@ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully created user."),
@ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
@ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
@ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
}
)
@PostMapping("/create-admin")
ResponseEntity<UserDTO> createAdmin(@RequestBody @Valid UserCreateDTO userCreateDTO) throws NegociationRulesException;

///////////////////////////////// SERVICE USUARIO /////////////////////////////////

public UserDTO registerAdmin(UserCreateDTO userCreateDTO) throws NegociationRulesException {
checkEmailExist(userCreateDTO.getEmail());
UserEntity userEntity = createToEntity(userCreateDTO);
userEntity.setRole(rolesService.findByRoleName("ROLE_ADMIN"));
return entityToDTO(userRepository.save(userEntity));
}

/////////////////////////////// SERVICE RETROSPECTIVA //////////////////////////////

public List<RetrospectiveDTO> listAll() {
return retrospectiveRepository.findAll().stream()
.map(this::entityToDTO)
.toList();
}

public void delete(Integer idRetrospective) throws NegociationRulesException {
RetrospectiveEntity retrospectiveEntity = findById(idRetrospective);
retrospectiveRepository.delete(retrospectiveEntity);
}

//////////////////////////// CONTROLLER RETROSPECTIVA /////////////////////////////

@Operation(summary = "Remove retrospective and all data")
@ApiResponses(
value = {
@ApiResponse(responseCode = "200", description = "Sucess! Retrospective successfully deleted."),
 @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
 @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
 @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
}
)
@DeleteMapping("/delete/{idRetrospective}")
void delete(@PathVariable("idRetrospective") Integer idRetrospective) throws NegociationRulesException;

@Operation(summary = "Update retrospective")
@ApiResponses(
value = {
@ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully updated retrospective."),
@ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
@ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
@ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
}
)
@PutMapping("/update/{idRetrospective}")
ResponseEntity<RetrospectiveDTO> update(@PathVariable("idRetrospective") Integer idRetrospective,
RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException;

@Operation(summary = "List all retrospective")
@ApiResponses(
value = {
@ApiResponse(responseCode = "200", description = "Sucess! Returns the list of all retrospectives."),
@ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
@ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
@ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
}
)
@GetMapping("/list")
ResponseEntity<List<RetrospectiveDTO>> listAll();

/////////////////////////////////////////// SERVICE KUDOBOX //////////////////////////////
public void delete(Integer idKudoBox) throws NegociationRulesException {
KudoBoxEntity kudoBoxEntity = findById(idKudoBox);
kudoBoxRepository.delete(kudoBoxEntity);
}

CONTROLLER KUDOBOX

    @Operation(summary = "Delete kudobox")
    @DeleteMapping("/delete/{idKudoBox}")
    public ResponseEntity<Void> delete(@PathVariable("idKudoBox") Integer idKudoBox) throws NegociationRulesException {
        kudoBoxService.delete(idKudoBox);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

DOCUMENTAÇÃO KUDOBOX

    @Operation(summary = "Delete kudobox")
    @DeleteMapping("/delete/{idKudoBox}")
    public ResponseEntity<Void> delete(@PathVariable("idKudoBox") Integer idKudoBox) throws NegociationRulesException {
        kudoBoxService.delete(idKudoBox);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @Operation(summary = "Update retrospective item")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Sucess! Returns the successfully created kudocard."),
//                    @ApiResponse(responseCode = "403", description = "Invalid Permission! You do not have permission to acesses."),
//                    @ApiResponse(responseCode = "400", description = "Bad Request! Invalid parameters"),
//                    @ApiResponse(responseCode = "500", description = "Error! Could not connect to the server.")
//            }
//    )
//    @PostMapping("/update/{idKudocard}")
//    ResponseEntity<KudoCardDTO> update(@PathVariable("idKudocard") Integer idItem, KudoCardUpdateDTO kudoCardUpdateDTO) throws NegociationRulesException;

public KudoCardDTO update(Integer idKudoCard, KudoCardUpdateDTO kudoCardUpdateDTO) throws NegociationRulesException {

        KudoCardEntity kudoCardEntityRecovered = findById(idKudoCard);
        KudoCardEntity kudoCardEntityUpdate = updateToEntity(kudoCardUpdateDTO);

        if (kudoCardUpdateDTO.getTitle() == null) {
            kudoCardEntityUpdate.setTitle(kudoCardEntityRecovered.getTitle());
        }
        if (kudoCardUpdateDTO.getSender() == null) {
            kudoCardEntityUpdate.setSender(kudoCardEntityRecovered.getSender());
        }
        if (kudoCardUpdateDTO.getReceiver() == null) {
            kudoCardEntityUpdate.setReceiver(kudoCardEntityRecovered.getReceiver());
        }

        kudoCardEntityUpdate.setIdKudoCard(idKudoCard);
        kudoCardEntityUpdate.setIdCreator(kudoCardEntityRecovered.getIdCreator());

        return entityToDTO(kudoCardRepository.save(kudoCardEntityUpdate));
    }


    public List<ItemRetrospectiveDTO> listByIdRetrospective(Integer idRetrospective) {
        return findByIdRetrospective(idRetrospective).stream()
                .map(this::entityToDTO)
                .toList();
    }

    public RetrospectiveDTO update(Integer id, RetrospectiveCreateDTO retrospectiveCreateDTO) throws NegociationRulesException {
        listById(id);

        RetrospectiveEntity retrospectiveEntity = createToEntity(retrospectiveCreateDTO);
        retrospectiveEntity.setIdRetrospective(id);

        return entityToDTO(retrospectiveRepository.save(retrospectiveEntity));
    }
