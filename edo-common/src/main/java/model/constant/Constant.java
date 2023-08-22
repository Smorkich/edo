package model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

    public static final String ADDRESS_URL = "api/repository/address";
    public static final String APPEAL_URL = "api/repository/appeal";
    public static final String APPROVAL_URL = "/api/service/approval";
    public static final String APPROVAL_REST_URL = "/api/rest/approval";
    public static final String APPROVAL_SERVICE_URL = "/api/service/approval";
    public static final String AUTHOR_URL = "api/repository/author";
    public static final String APPEAL_REST_URL = "/api/edo/appeal";
    public static final String SERVICE_APPEAL_URL = "/api/service/appeal";
    public static final String EXECUTION_REPORT_URL = "api/repository/report";
    public static final String DEPARTMENT_URL = "api/repository/department";
    public static final String DEPARTMENT_SERVICE_URL = "api/service/department";
    public static final String EMPLOYEE_URL = "api/repository/employee";
    public static final String FILEPOOL_URL = "api/repository/filePool";
    public static final String NOMENCLATURE_URL = "api/repository/nomenclature";
    public static final String QUESTION_URL = "api/repository/question";
    public static final String RESOLUTION_URL = "api/repository/resolution";
    public static final String RESOLUTION_REST_URL = "api/rest/resolution";
    public static final String RESOLUTION_SERVICE_URL = "api/service/resolution";
    public static final String REGION_URL = "api/repository/region";
    public static final String THEME_URL = "api/repository/theme";

    public static final String EDO_REPOSITORY_NAME = "edo-repository";

    public static final String EDO_SERVICE_NAME = "edo-service";
    public static final String EDO_REST_NAME = "edo-rest";

    public static final String NOMENCLATURE_PARAMETER = "index";
    public static final String EMPLOYEE_FIO_SEARCH_PARAMETER = "fullName";
    public static final String EDO_FILE_STORAGE_NAME = "edo-file-storage";
    public static final String NOTIFICATION_URL = "api/repository/notification";
    public static final String EDO_INTEGRATION_NAME = "edo-integration";
    public static final String MESSAGE_URL = "api/integration/message";
    public static final String REST_TO_SERVICE_APPROVAL_QUEUE = "RestToServiceApprovalQueue";

    public static final String REST_TO_SERVICE_APPEAL_QUEUE = "RestToServiceAppealQueue";
    public static final String FILE_RECOGNITION_START = "FileRecognitionStart";
    public static final String DEADLINE_CHANGE_QUEUE = "DeadlineChange";
    public static final String REST_TO_SERVICE_NOMENCLATURE_QUEUE = "RestToServiceNomenclatureQueue";

    public static final String PDF_CONTENT_TYPE = "application/pdf";
    public static final String PDF = "pdf";
}
