import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HrManagerProjectManagerViewComponent } from './hr-manager-project-manager-view.component';

describe('HrManagerProjectManagerViewComponent', () => {
  let component: HrManagerProjectManagerViewComponent;
  let fixture: ComponentFixture<HrManagerProjectManagerViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HrManagerProjectManagerViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HrManagerProjectManagerViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
