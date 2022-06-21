package com.rdaniel.sd.project.device;

import com.rdaniel.sd.project.BaseControllerTest;
import com.rdaniel.sd.project.device.dto.DeviceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.project.TestCreationFactory.listOf;
import static com.rdaniel.sd.project.TestCreationFactory.newDeviceDto;
import static com.rdaniel.sd.project.UrlMappings.DEVICES_PATH;
import static com.rdaniel.sd.project.UrlMappings.DEVICE_ID_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeviceControllerTest extends BaseControllerTest {

  @InjectMocks
  private DeviceController controller;

  @Mock
  private DeviceService deviceService;

  @BeforeEach
  protected void setUp() {
    super.setUp();
    controller = new DeviceController(deviceService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
  }

  @Test
  void getAllCustomerDevices_returnedList_status200() throws Exception {
    List<DeviceDto> books = listOf(DeviceDto.class, 5);
    when(deviceService.findAllByCustomerId(any(Long.class))).thenReturn(books);

    ResultActions response = performGetWithPathVariable(DEVICES_PATH, 1L);
    response.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonContentToBe(books));

    verify(deviceService, times(1)).findAllByCustomerId(any(Long.class));
  }


  @Test
  void getDevice_validIdentifier_status200() throws Exception {
    final DeviceDto deviceDto = newDeviceDto();
    when(deviceService.findById(any(Long.class))).thenReturn(deviceDto);

    ResultActions response = performGetWithPathVariables(DEVICES_PATH + DEVICE_ID_PATH, 1L, 1L);
    response.andExpect(status().isOk())
        .andExpect(jsonContentToBe(deviceDto));

    verify(deviceService, times(1)).findById(any(Long.class));
    verifyNoMoreInteractions(deviceService);
  }

  @Test
  void getDevice_invalidIdentifier_status404() throws Exception {
    when(deviceService.findById(any(Long.class))).thenThrow(EntityNotFoundException.class);

    ResultActions response = performGetWithPathVariables(DEVICES_PATH + DEVICE_ID_PATH, 1L, 1L);
    response.andExpect(status().isNotFound());

    verify(deviceService, times(1)).findById(any(Long.class));
  }

  @Test
  void createDevice_validData_status201() throws Exception {
    final DeviceDto deviceDto = newDeviceDto();
    when(deviceService.create(any(Long.class), any(DeviceDto.class))).thenReturn(deviceDto);

    ResultActions response = performPostWithRequestBodyAndPathVariables(DEVICES_PATH, deviceDto, 1L);
    response.andExpect(status().isCreated())
        .andExpect(jsonContentToBe(deviceDto));

    verify(deviceService, times(1)).create(any(Long.class), any(DeviceDto.class));
  }

  @Test
  void updateDevice_validIdentifierAndContent_status200() throws Exception {
    final DeviceDto deviceDto = newDeviceDto();
    when(deviceService.update(any(Long.class), any(DeviceDto.class))).thenReturn(deviceDto);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariables(DEVICES_PATH + DEVICE_ID_PATH, deviceDto, 1L, 1L);
    resultActions.andExpect(status().isOk())
        .andExpect(jsonContentToBe(deviceDto));

    verify(deviceService, times(1)).update(any(Long.class), any(DeviceDto.class));
  }

  @Test
  void updateDevice_invalidIdentifier_status404() throws Exception {
    final DeviceDto deviceDto = newDeviceDto();
    when(deviceService.update(any(Long.class), any(DeviceDto.class))).thenThrow(EntityNotFoundException.class);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariables(DEVICES_PATH + DEVICE_ID_PATH, deviceDto, 1L, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(deviceService, times(1)).update(any(Long.class), any(DeviceDto.class));
  }

  @Test
  void deleteDevice_validIdentifier_status204() throws Exception {
    mockMvc.perform(delete(DEVICES_PATH + DEVICE_ID_PATH, 1L, 1L))
        .andExpect(status().isNoContent());

    verify(deviceService, times(1)).delete(any(Long.class));
  }

  @Test
  void deleteDevice_invalidIdentifier_status404() throws Exception {
    doThrow(EntityNotFoundException.class).when(deviceService).delete(any(Long.class));

    final ResultActions resultActions = performDeleteWithPathVariables(DEVICES_PATH + DEVICE_ID_PATH, 1L, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(deviceService, times(1)).delete(any(Long.class));
  }
}