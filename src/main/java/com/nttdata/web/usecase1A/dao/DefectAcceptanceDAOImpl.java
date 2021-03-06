package com.nttdata.web.usecase1A.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nttdata.web.model.ReleaseDetails;
import com.nttdata.web.usecase1A.model.DefectAcceptanceModelTelephonica;
import com.nttdata.web.utils.CrestaQueryConstants;

@Repository
public class DefectAcceptanceDAOImpl implements DefectAcceptanceDAO {

	@Autowired
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	
	@Override
	public List<DefectAcceptanceModelTelephonica> getDefectAcceptanceAndReleaseDataTelephonica(String userId) {
		String sqlQuery = CrestaQueryConstants.QRY_GET_DEFECT_DENSITY_DATA_FOR_USECASE1A_TELEPHONICA;
		List<DefectAcceptanceModelTelephonica> defectDensityModelList = jdbcTemplate.query(sqlQuery,
				new Object[] { Integer.parseInt(userId) }, new RowMapper<DefectAcceptanceModelTelephonica>() {
					@Override
					public DefectAcceptanceModelTelephonica mapRow(ResultSet rs, int rowNum) throws SQLException {
						DefectAcceptanceModelTelephonica defectDensityModel = new DefectAcceptanceModelTelephonica();
						defectDensityModel.setRelease((rs.getInt("rel_id")));
						defectDensityModel.setDefectAcceptance((rs.getInt("acceptance")));
						return defectDensityModel;
					}
				});
		return defectDensityModelList;

	}

	public List<DefectAcceptanceModelTelephonica> getDefectAcceptanceDataTelephonica(int userid) {
		String sqlQuery = CrestaQueryConstants.QRY_GET_ACCEPTANCE;
		List<DefectAcceptanceModelTelephonica> defectAcceptanceModelList = jdbcTemplate.query(sqlQuery,
				new Object[] { userid }, new RowMapper<DefectAcceptanceModelTelephonica>() {
					@Override
					public DefectAcceptanceModelTelephonica mapRow(ResultSet rs, int rowNum) throws SQLException {
						DefectAcceptanceModelTelephonica defectAcceptanceModel = new DefectAcceptanceModelTelephonica();

						defectAcceptanceModel.setRelease((rs.getInt("rel_id")));
						defectAcceptanceModel.setDefectAcceptance((rs.getInt("acceptance")));
						defectAcceptanceModel.setKiloLinesOfCode(rs.getInt("KLOC"));
						defectAcceptanceModel.setTestCaseCount(rs.getInt("test_case_count"));
						defectAcceptanceModel.setApplicationComplexity(rs.getInt("application_complexity"));
						defectAcceptanceModel.setDomainKnowledge(rs.getInt("domain_knowledge"));
						defectAcceptanceModel.setTechnicalSkills(rs.getInt("technical_skills"));
						defectAcceptanceModel.setRequirementQueryCount(rs.getInt("requirements_query_count"));
						defectAcceptanceModel.setCodeReviewComments((rs.getInt("code_review_comments")));
						defectAcceptanceModel.setDesignReviewComments((rs.getInt("design_review_comments")));
						return defectAcceptanceModel;
					}
				});
		return defectAcceptanceModelList;
	}

	@Override
	public List<ReleaseDetails> getLastTenPredictions(int algorithmId) {
		// TODO Auto-generated method stub
		String sqlQuery = CrestaQueryConstants.QRY_GET_LAST_TEN_PREDICTIONS_DEFECT_ACCEPTANCE;

		List<ReleaseDetails> lastTenPredictions = jdbcTemplate.query(sqlQuery, new Object[] {},
				new RowMapper<ReleaseDetails>() {
					@Override
					public ReleaseDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
						ReleaseDetails lastPrediction = new ReleaseDetails();
						lastPrediction.setReleaseid(rs.getInt("releaseid"));
						switch (algorithmId) {
						case 0:
							lastPrediction.setPredictedValueInt(rs.getInt("uc1AJubatus"));
							break;
						case 2:
							lastPrediction.setPredictedValueInt(rs.getInt("uc1ALinearRegression"));
							break;
						case 3:
							lastPrediction.setPredictedValueInt(rs.getInt("uc1ASVRLinear"));
							break;
						case 4:
							lastPrediction.setPredictedValueInt(rs.getInt("uc1ASVRRBF"));
							break;
						default:
							break;
						}

						return lastPrediction;
					}
				});
		return lastTenPredictions;
	}
}
