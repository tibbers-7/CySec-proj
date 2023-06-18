import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HrManagerProfileComponent } from './hr-manager-profile.component';

describe('HrManagerProfileComponent', () => {
  let component: HrManagerProfileComponent;
  let fixture: ComponentFixture<HrManagerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HrManagerProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HrManagerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
