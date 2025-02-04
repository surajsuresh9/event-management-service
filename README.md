- For controller post method testing, object mapper needs to serialize Event object with Date field

- Add the required JSR-310 dependency and register it with the object mapper using objectMapper.findAndRegisterModules()

- Add the JsonFormat for the LocalDateTime fields

============================

ControllerTest

@WebMvcTest

@AutoWired
MockMvc

@MockitoBean
EventService
