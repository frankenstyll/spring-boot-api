package com.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.springboot.model.PostDueDateDebtDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class TestController {
	
	@Autowired
	Gson gson;
	
	@GetMapping("/api/test/getApi")
	@ApiOperation(value = "ทดสอบ API", notes = "ส่ง GET Method")
	public ResponseEntity<String> testApi (
	    @ApiParam(value="username Header : root", required = true) @RequestHeader(name="username") String username ,
		@ApiParam(value="password Header : 1234", required = true) @RequestHeader(name="password") String password ,
		@ApiParam(value = "ID Card or Passport Number", required = true) @RequestParam(name = "customerID", defaultValue = "123456") String customerID )
	{
		  
		Map<String,Object> mp = new HashMap<String, Object>();
		
		if( !"root".equals(username) || !"1234".equals(password)) {
			mp.put("status", "fail");
			mp.put("message","Username or Password is Invalid");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(gson.toJson(mp));
		}
		
		
		mp.put("status", "success");
		mp.put("message","");
		mp.put("customerID", customerID);
		  
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(mp));
	   //return HttpResponse.ok(Base64.getDecoder().decode(genReportDto.getFileContent())).contentType(genReportDto.getContentType()).headers(headers);
	}
	
	@PostMapping(value = "/api/test/postApi")
	@ApiOperation(value = "ทดสอบ API", notes = "ส่ง POST Method")
	public ResponseEntity<Object>  testPostApi(
		    @ApiParam(value="username Header : root", required = true) @RequestHeader(name="username") String username ,
			@ApiParam(value="password Header : 1234", required = true) @RequestHeader(name="password") String password ,
			@ApiParam(value="Post due date Object", required = true) @RequestBody PostDueDateDebtDTO postDueDateDebt){
		
		Map<String,Object> mp = new HashMap<String, Object>();
		
		if( !"root".equals(username) || !"1234".equals(password)) {
			mp.put("status", "fail");
			mp.put("message","Username or Password is Invalid");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(gson.toJson(mp));
		}
		
		mp.put("status", "success");
		mp.put("message","");
		mp.put("PostDueDateDept", postDueDateDebt);
		
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(mp));
	}

}
