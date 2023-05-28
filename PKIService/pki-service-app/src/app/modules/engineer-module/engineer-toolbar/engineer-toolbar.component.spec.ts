import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerToolbarComponent } from './engineer-toolbar.component';

describe('EngineerToolbarComponent', () => {
  let component: EngineerToolbarComponent;
  let fixture: ComponentFixture<EngineerToolbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EngineerToolbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EngineerToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
