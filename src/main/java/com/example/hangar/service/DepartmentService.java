@Service

public class DepartmentService {
  private final DepartmentRepository repository;

  public DepartmentService(DepartmentRepository repository) {
    this.repository = repository;
  }
}
