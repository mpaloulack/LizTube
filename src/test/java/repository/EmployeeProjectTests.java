package repository;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.config.JpaConfigs;
import com.liztube.entity.Employee;
import com.liztube.entity.Project;
import com.liztube.repository.EmployeeRepository;
import com.liztube.repository.ProjectRepository;
import com.liztube.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/EmployeeProjectDataset.xml", tearDownOperation = DBOperation.DELETE_ALL)
public class EmployeeProjectTests {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void should_create_relation_with_employee_with_join_value_true(){

        //assertThat(projectRepository.count()).isEqualTo(1);

        //Project project = new Project();
        //project.addEmployee(new Employee(), false);
        //project = projectRepository.saveAndFlush(project);

        assertThat(projectRepository.count()).isEqualTo(2);
        Project projectSavedBefore=projectRepository.findOne((long)1);
        assertThat(projectSavedBefore.getEmployees().get(0).isProjectLead()).isEqualTo(true);

        //List<Project> projects = projectRepository.findAll();

        //Project projectSavedAfter=projectRepository.findOne((long)2);
        //assertThat(projectSavedAfter.getEmployees().get(0).isProjectLead()).isEqualTo(false);
    }

}
