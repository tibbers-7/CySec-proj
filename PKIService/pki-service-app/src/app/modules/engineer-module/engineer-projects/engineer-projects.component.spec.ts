import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerProjectsComponent } from './engineer-projects.component';

describe('EngineerProjectsComponent', () => {
  let component: EngineerProjectsComponent;
  let fixture: ComponentFixture<EngineerProjectsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EngineerProjectsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EngineerProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
