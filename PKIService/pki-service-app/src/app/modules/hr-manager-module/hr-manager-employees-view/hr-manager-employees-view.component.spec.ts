import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HrManagerEmployeesViewComponent } from './hr-manager-employees-view.component';

describe('HrManagerEmployeesViewComponent', () => {
  let component: HrManagerEmployeesViewComponent;
  let fixture: ComponentFixture<HrManagerEmployeesViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HrManagerEmployeesViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HrManagerEmployeesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
