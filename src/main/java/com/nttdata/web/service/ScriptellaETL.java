package com.nttdata.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.web.controllers.LoginController;
import com.nttdata.web.dao.ConfigDAO;
import com.nttdata.web.model.PredictedModel;
import com.nttdata.web.usecase3.model.DefectLeakageModel;

import routines.system.StringUtils;
import scriptella.driver.spring.EtlExecutorBean;
import scriptella.execution.EtlExecutorException;

@Service
public class ScriptellaETL implements ETL {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	JubatusProcessor jubatusProcessor;
	
	@Autowired
	ConfigDAO configDAO;

		public EtlExecutorBean getPredictDefectDeferralTelephonicaBean() {
		return predictDefectDeferralTelephonicaBean;
	}

	public void setPredictDefectDeferralTelephonicaBean(EtlExecutorBean predictDefectDeferralTelephonicaBean) {
		this.predictDefectDeferralTelephonicaBean = predictDefectDeferralTelephonicaBean;
	}

	@Autowired
	@Resource(name = "predictLeakage")
	protected EtlExecutorBean predictLeakageBean;
	
	public EtlExecutorBean getDefectiveModulesBean() {
		return defectiveModulesBean;
	}

	public void setDefectiveModulesBean(EtlExecutorBean defectiveModulesBean) {
		this.defectiveModulesBean = defectiveModulesBean;
	}

	@Autowired
	@Resource(name = "defectiveModules")
	protected EtlExecutorBean defectiveModulesBean;
	
	@Autowired
	@Resource(name = "predictDefectDensityUseCaseTelephonica")
	protected EtlExecutorBean predictDefectDensityUseCaseTelephonicaBean;
	
	@Autowired
	@Resource(name = "predictDefectDeferralTelephonica")
	protected EtlExecutorBean predictDefectDeferralTelephonicaBean;
	
	@Autowired
	@Resource(name = "predictDefectAcceptanceTelephonica")
	protected EtlExecutorBean predictDefectAcceptanceTelephonicaBean;
	
	@Autowired
	@Resource(name = "predictDefectCountTelephonica")
	protected EtlExecutorBean predictDefectCountTelephonicaBean;
	
	@Autowired
	@Resource(name = "predictFunctionalDefectCountTelephonica")
	protected EtlExecutorBean predictFunctionalDefectCountTelephonicaBean;

	public EtlExecutorBean getPredictFunctionalDefectCountTelephonicaBean() {
		return predictFunctionalDefectCountTelephonicaBean;
	}

	public void setPredictFunctionalDefectCountTelephonicaBean(
			EtlExecutorBean predictFunctionalDefectCountTelephonicaBean) {
		this.predictFunctionalDefectCountTelephonicaBean = predictFunctionalDefectCountTelephonicaBean;
	}

	public EtlExecutorBean getPredictDefectCountTelephonicaBean() {
		return predictDefectCountTelephonicaBean;
	}

	public void setPredictDefectCountTelephonicaBean(EtlExecutorBean predictDefectCountTelephonicaBean) {
		this.predictDefectCountTelephonicaBean = predictDefectCountTelephonicaBean;
	}

	public EtlExecutorBean getPredictDefectAcceptanceTelephonicaBean() {
		return predictDefectAcceptanceTelephonicaBean;
	}

	public void setPredictDefectAcceptanceTelephonicaBean(EtlExecutorBean predictDefectAcceptanceTelephonicaBean) {
		this.predictDefectAcceptanceTelephonicaBean = predictDefectAcceptanceTelephonicaBean;
	}

	@Override
	public List<Integer> interactETL(String eTLType, String predictionId, String metricsId,String userId, int redmineProjectId,int algorithmId) {
		if (eTLType.equalsIgnoreCase("calculateUclLcl")) {
		} 
		else if (eTLType.equalsIgnoreCase("predictUseCase1")) {
			return predictUseCase1(userId, String.valueOf(redmineProjectId), predictionId,algorithmId);
		}
		else if (eTLType.equalsIgnoreCase("predictUseCase1A")) {
			return predictUseCase1A(userId, String.valueOf(redmineProjectId), predictionId,algorithmId);
		}
		else if (eTLType.equalsIgnoreCase("predictUseCase3")) {
			return predictUseCase3(userId, String.valueOf(redmineProjectId), predictionId);
		}
		else if (eTLType.equalsIgnoreCase("predictUseCase1B")) {
			return predictUseCase1B(userId, String.valueOf(redmineProjectId), predictionId,algorithmId);
		}else if (eTLType.equalsIgnoreCase("predictUseCase1C")) {
			return predictUseCase1C(userId, String.valueOf(redmineProjectId), predictionId,algorithmId);
		}else if (eTLType.equalsIgnoreCase("predictUseCase1D")) {
			return predictUseCase1D(userId, String.valueOf(redmineProjectId), predictionId,algorithmId);
		}else if (eTLType.equalsIgnoreCase("predictDefectCountBoA")) {
			return predictDefectCountBoA(userId, String.valueOf(redmineProjectId), predictionId,algorithmId);
		}
		return null;
	}

	public List<int[]> interactETLForUclLcl(String eTLType, String predictionId, String metricsId,String userId, int redmineProjectId,List<Integer> defectCount) {
		if (eTLType.equalsIgnoreCase("calculateUclLcl")) {
			return retrieveUclLclData(String.valueOf(redmineProjectId), Integer.parseInt(metricsId),defectCount);
		} 
		return null;
	}
	
	private List<int[]> retrieveUclLclData(String projectId, int metricsId,List<Integer> defectCount) {
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("projectId", StringUtils.valueOf(projectId));

		return jubatusProcessor.computeUclLclData(metricsId,defectCount);
	}
	
	public List<int[]> interactETLForUclLcl1(String eTLType, String predictionId, String metricsId,String userId, int redmineProjectId,List<DefectLeakageModel> defectCount) {
		if (eTLType.equalsIgnoreCase("calculateUclLcl")) {
			return retrieveUclLclData1(String.valueOf(redmineProjectId), Integer.parseInt(metricsId),defectCount);
		} 
		return null;
	}
	
	private List<int[]> retrieveUclLclData1(String projectId, int metricsId,List<DefectLeakageModel> defectCount) {
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("projectId", StringUtils.valueOf(projectId));
		
		return jubatusProcessor.computeUclLclData1(metricsId,defectCount);
	}

	
	private List<Integer> predictUseCase1A(String userId, String projectId, String predictionId,int algorithmId) {
		try {
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("userId", userId);
			properties.put("projectId", StringUtils.valueOf(projectId));
			properties.put("predictionCode", predictionId);
			
			predictDefectAcceptanceTelephonicaBean.setProperties(properties);
			try {
				predictDefectAcceptanceTelephonicaBean.setConfiguration(null);
				predictDefectAcceptanceTelephonicaBean.afterPropertiesSet();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			predictDefectAcceptanceTelephonicaBean.execute();
		} catch (EtlExecutorException etlExecutorException) {
			log.debug("Error executing ETL file" + etlExecutorException);
			etlExecutorException.printStackTrace();
		}
		System.out.println("executed");
		if(algorithmId == 0){
			return jubatusProcessor.predictDensityForUseCase1A(Integer.parseInt(userId),predictionId);
			}else if(algorithmId > 1){
				return jubatusProcessor.getPredictionResultUseCase1A(predictionId,userId,algorithmId);
			}else if(algorithmId == 1){
				return jubatusProcessor.getAllPredictionResultUseCase1A(predictionId, userId, algorithmId);
			}
			return null;
		
		
	}
	
	private List<Integer> predictUseCase1B(String userId, String projectId, String predictionId,int algorithmId) {
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("userId", userId);
		properties.put("projectId", StringUtils.valueOf(projectId));
		properties.put("predictionCode", predictionId);
		
		predictDefectDeferralTelephonicaBean.setProperties(properties);
		try {
			predictDefectDeferralTelephonicaBean.setConfiguration(null);
			predictDefectDeferralTelephonicaBean.afterPropertiesSet();
			predictDefectDeferralTelephonicaBean.execute();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("executed");
		
		if(algorithmId == 0){
			return jubatusProcessor.predictDefectDeferralRate(Integer.parseInt(userId),predictionId);
			}else if(algorithmId > 1){
				return jubatusProcessor.getPredictionResultUseCase1B(predictionId,userId,algorithmId);
			}else if(algorithmId == 1){
				return jubatusProcessor.getAllPredictionResultUseCase1B(predictionId, userId, algorithmId);
			}
			return null;
		
	}
	
	private List<Integer> predictUseCase3(String userId, String projectId, String predictionId) {
		try {
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("userId", userId);
			properties.put("projectId", StringUtils.valueOf(projectId));
			properties.put("predictionCode", predictionId);
			
			predictLeakageBean.setProperties(properties);
			try {
				predictLeakageBean.setConfiguration(null);
				predictLeakageBean.afterPropertiesSet();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			predictLeakageBean.execute();
		} catch (EtlExecutorException etlExecutorException) {
			log.debug("Error executing ETL file" + etlExecutorException);
			etlExecutorException.printStackTrace();
		}
		System.out.println("executed");
		return jubatusProcessor.predictDensityForUseCase3(Integer.parseInt(userId),predictionId);
	}
	
	private List<Integer> predictUseCase1C(String userId, String projectId, String predictionId,int algorithmId) {
		try {
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("userId", userId);
			properties.put("projectId", StringUtils.valueOf(projectId));
			predictDefectCountTelephonicaBean.setProperties(properties);
			try {
				predictDefectCountTelephonicaBean.setConfiguration(null);
				predictDefectCountTelephonicaBean.afterPropertiesSet();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			predictDefectCountTelephonicaBean.execute();
		} catch (EtlExecutorException etlExecutorException) {
			log.debug("Error executing ETL file" + etlExecutorException);
			etlExecutorException.printStackTrace();
		}
		if(algorithmId == 0){
			return jubatusProcessor.predictDefectCountRate(Integer.parseInt(userId),predictionId);
			}else if(algorithmId > 1){
				return jubatusProcessor.getPredictionResultUseCase1C(predictionId,userId,algorithmId);
			}else if(algorithmId == 1){
				return jubatusProcessor.getAllPredictionResultUseCase1C(predictionId, userId, algorithmId);
			}
			return null;
	}

	private List<Integer> predictUseCase1D(String userId, String projectId, String predictionId,int algorithmId) {
		try {
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("userId", userId);
			properties.put("projectId", StringUtils.valueOf(projectId));
			properties.put("predictionCode", predictionId);
		
			predictFunctionalDefectCountTelephonicaBean.setProperties(properties);
			try {
				predictFunctionalDefectCountTelephonicaBean.setConfiguration(null);
				predictFunctionalDefectCountTelephonicaBean.afterPropertiesSet();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			predictFunctionalDefectCountTelephonicaBean.execute();
		} catch (EtlExecutorException etlExecutorException) {
			log.debug("Error executing ETL file" + etlExecutorException);
			etlExecutorException.printStackTrace();
		}
		System.out.println("executed");
		if(algorithmId == 0){
		return jubatusProcessor.predictDensityForUseCase1D(Integer.parseInt(userId),predictionId);
		}else if(algorithmId > 1){
			return jubatusProcessor.getPredictionResultUseCase1D(predictionId,userId,algorithmId);
		}else if(algorithmId == 1){
			return jubatusProcessor.getAllPredictionResultUseCase1D(predictionId, userId, algorithmId);
		}
		return null;
	}
	
	private List<Integer> predictDefectCountBoA(String userId, String projectId, String predictionId,int algorithmId) {
		
		if(algorithmId == 0){
		return jubatusProcessor.predictDensityForUseCase1D(Integer.parseInt(userId),predictionId);
		}else if(algorithmId > 1){
			return jubatusProcessor.predictDefectCountBoA(algorithmId);
		}else if(algorithmId == 1){
			return jubatusProcessor.getAllPredictionResultUseCase1D(predictionId, userId, algorithmId);
		}
		return null;
	}
	
	
	private List<Integer> predictUseCase1(String userId, String projectId, String predictionId,int algorithmId) {
		try {
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("userId", userId);
			properties.put("projectId", StringUtils.valueOf(projectId));
			properties.put("predictionCode", predictionId);
			predictDefectDensityUseCaseTelephonicaBean.setProperties(properties);
		try {
			predictDefectDensityUseCaseTelephonicaBean.setConfiguration(null);
			predictDefectDensityUseCaseTelephonicaBean.afterPropertiesSet();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		predictDefectDensityUseCaseTelephonicaBean.execute();
	} catch (EtlExecutorException etlExecutorException) {
		log.debug("Error executing ETL file" + etlExecutorException);
		etlExecutorException.printStackTrace();
	}
	System.out.println("executed");
		if(algorithmId == 0){
			return jubatusProcessor.predictDensityForUseCase1(Integer.parseInt(userId),predictionId);
			}else if(algorithmId > 1){
				return jubatusProcessor.getPredictionResultTelephonicaUseCase1(predictionId,userId,algorithmId);
			}else if(algorithmId == 1){
				return jubatusProcessor.getAllPredictionResultTelephonicaUseCase1(predictionId, userId, algorithmId);
			}
			return null;
		
	}

	public void setPredictLeakageBean(EtlExecutorBean predictLeakageBean) {
		this.predictLeakageBean = predictLeakageBean;
	}

	public EtlExecutorBean getPredictDefectDensityUseCaseTelephonicaBean() {
		return predictDefectDensityUseCaseTelephonicaBean;
	}

	public void setPredictDefectDensityUseCaseTelephonicaBean(EtlExecutorBean predictDefectDensityUseCaseTelephonicaBean) {
		this.predictDefectDensityUseCaseTelephonicaBean = predictDefectDensityUseCaseTelephonicaBean;
	}

	@Override
	public Map<String, PredictedModel> interactETLForUseCase2(String eTLType, String predictionId, String metricsId,
			String userId, int redmineProjectId) {
		if (eTLType.equalsIgnoreCase("calculateUclLcl")) {
			/*List<Double> uclLclList = retrieveUclLclData(String.valueOf(redmineProjectId),0);
			Map uclLclMap = new HashMap<String, PredictedModel>();
			PredictedModel predictedModel = new PredictedModel();
			predictedModel.setUcl(uclLclList.get(1));
			predictedModel.setLcl(uclLclList.get(1));
			/*uclLclMap.put("LCL",uclLclList.get(0));
			uclLclMap.put("UCL",uclLclList.get(1));
			uclLclMap.put(metricsId, predictedModel);
			return uclLclMap;*/
		} else if (eTLType.equalsIgnoreCase("predictUseCase2")) {
			return predictUseCase2(userId, String.valueOf(redmineProjectId), predictionId);
		}
		
		return null;
		
	}	
	
	
	
	
	private Map<String, PredictedModel> predictUseCase2(String userId, String projectId, String predictionId) {
		try {
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("userId", userId);
			properties.put("projectId", StringUtils.valueOf(projectId));
			properties.put("predictionCode", predictionId);
			
			defectiveModulesBean.setProperties(properties);
			try {
				defectiveModulesBean.setConfiguration(null);
				defectiveModulesBean.afterPropertiesSet();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			defectiveModulesBean.execute();
		} catch (EtlExecutorException etlExecutorException) {
			log.debug("Error executing ETL file" + etlExecutorException);
			etlExecutorException.printStackTrace();
		}
	
		System.out.println("executed");
		return jubatusProcessor.predictDensityModuleWise(Integer.parseInt(userId), predictionId);
	}

}
