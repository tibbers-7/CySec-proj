import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerUpdateProjectComponent } from './engineer-update-project.component';

describe('EngineerUpdateProjectComponent', () => {
  let component: EngineerUpdateProjectComponent;
  let fixture: ComponentFixture<EngineerUpdateProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EngineerUpdateProjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EngineerUpdateProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
