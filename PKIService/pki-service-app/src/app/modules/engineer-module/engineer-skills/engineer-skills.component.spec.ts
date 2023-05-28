import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerSkillsComponent } from './engineer-skills.component';

describe('EngineerSkillsComponent', () => {
  let component: EngineerSkillsComponent;
  let fixture: ComponentFixture<EngineerSkillsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EngineerSkillsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EngineerSkillsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
