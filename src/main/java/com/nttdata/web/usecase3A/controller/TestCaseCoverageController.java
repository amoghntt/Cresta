package com.nttdata.web.usecase3A.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nttdata.web.usecase3A.model.TestCaseCoverageBean;
import com.nttdata.web.usecase3A.service.TestCaseCoverageService;

@Controller
public class TestCaseCoverageController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	private TestCaseCoverageService testCaseCoverageService;

	@RequestMapping(value = "/testcoverage", method = RequestMethod.POST)
	public ModelAndView viewTestCaseCoverage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String projectName = null;

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				// Parse the request
				List<FileItem> items = upload.parseRequest(request);
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {

					FileItem item = (FileItem) iterator.next();
					if (item.isFormField()) {
						if (item.getFieldName().equals("projectName")) {
							projectName = item.getString();
						} else if (item.getFieldName().equals("requirementText")) {
							testCaseCoverageService.writeRequirementToFile(item);
						}

					}
					if (!item.isFormField()) {
						item.getName();
						String root = "/opt/apache-tomcat-8.0.36/webapps/resources1/";
						//String root = "E:\\UploadedFiles";
						File path = new File(root);
						if (!path.exists()) {
							path.mkdirs();
						}

						// File uploadedFile = new File(path + "/" + fileName);
						// Hard coded the file name to be uploaded in server
						// repository...
						File uploadedFile = new File(path + "/" + "test-case.xlsm");
						System.out.println(uploadedFile.getAbsolutePath());
						item.write(uploadedFile);
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		List<TestCaseCoverageBean> testCaseResultList = new ArrayList<TestCaseCoverageBean>();
		testCaseResultList = testCaseCoverageService.getTestCaseCoverageResult();
		ModelAndView model = null;
		if(testCaseResultList != null){
		model = new ModelAndView("testCoverageResult");
		model.addObject("projectName", projectName);
		model.addObject("resultTestCoverage", testCaseResultList);
		
		}else{
			model = new ModelAndView("error");
		}
		return model;
	}


}
