import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordlessLoginPageComponent } from './passwordless-login-page.component';

describe('PasswordlessLoginPageComponent', () => {
  let component: PasswordlessLoginPageComponent;
  let fixture: ComponentFixture<PasswordlessLoginPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PasswordlessLoginPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasswordlessLoginPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
