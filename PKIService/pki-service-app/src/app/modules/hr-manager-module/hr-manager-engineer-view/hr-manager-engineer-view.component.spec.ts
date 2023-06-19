import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HrManagerEngineerViewComponent } from './hr-manager-engineer-view.component';

describe('HrManagerEngineerViewComponent', () => {
  let component: HrManagerEngineerViewComponent;
  let fixture: ComponentFixture<HrManagerEngineerViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HrManagerEngineerViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HrManagerEngineerViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
