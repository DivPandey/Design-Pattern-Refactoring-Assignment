package exercise;

import java.util.Objects;

public final class EmployeeDBAdapter implements Employee {
  private final EmployeeDB adaptee;

  public EmployeeDBAdapter(EmployeeDB adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public String getId() {
    return String.valueOf(adaptee.getId());
  }

  @Override
  public String getFirstName() {
    return adaptee.getFirstName();
  }

  @Override
  public String getLastName() {
    return adaptee.getSurname();
  }

  @Override
  public String getEmail() {
    return adaptee.getEmailAddress();
  }
}


