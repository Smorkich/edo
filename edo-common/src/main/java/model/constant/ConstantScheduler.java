package model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Kostenko Aleksandr
 * В этом обьекте лежит константы для ServiceExternalEmployeeImpl
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantScheduler {

    public static final String EDO_REPOSITORY_NAME = "edo-repository";
    public static final String URL_EMPLOYEE_PATH = "/api/repository/employee";
    public static final String URL_DEPARTMENT_PATH = "/api/repository/department";

}