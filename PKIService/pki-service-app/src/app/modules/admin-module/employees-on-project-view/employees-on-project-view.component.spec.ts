import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeesOnProjectViewComponent } from './employees-on-project-view.component';

describe('EmployeesOnProjectViewComponent', () => {
  let component: EmployeesOnProjectViewComponent;
  let fixture: ComponentFixture<EmployeesOnProjectViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmployeesOnProjectViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeesOnProjectViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
