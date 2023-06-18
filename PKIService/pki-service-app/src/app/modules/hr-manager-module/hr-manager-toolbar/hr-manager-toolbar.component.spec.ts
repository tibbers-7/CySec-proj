import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HrManagerToolbarComponent } from './hr-manager-toolbar.component';

describe('HrManagerToolbarComponent', () => {
  let component: HrManagerToolbarComponent;
  let fixture: ComponentFixture<HrManagerToolbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HrManagerToolbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HrManagerToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
