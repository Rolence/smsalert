/*
 * Copyright 2017 The Mifos Initiative.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mifos.smsalert.service.internal.command.handler;

import io.mifos.core.command.annotation.Aggregate;
import io.mifos.core.command.annotation.CommandHandler;
import io.mifos.core.command.annotation.CommandLogLevel;
import io.mifos.core.command.annotation.EventEmitter;
import io.mifos.smsalert.api.v1.events.SmsalertEventConstants;
import io.mifos.smsalert.service.internal.command.SampleCommand;
import io.mifos.smsalert.service.internal.repository.SampleJpaEntity;
import io.mifos.smsalert.service.internal.repository.SampleJpaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Aggregate
public class SampleAggregate {

  private final SampleJpaEntityRepository sampleJpaEntityRepository;

  @Autowired
  public SampleAggregate(final SampleJpaEntityRepository sampleJpaEntityRepository) {
    super();
    this.sampleJpaEntityRepository = sampleJpaEntityRepository;
  }

  //TODO: Think about your command handler logging, then delete this comment.
  // The log levels provided in the command handler cause log messages to be emitted each time this
  // command handler is called before and after the call. Before the call, the command is logged
  // using its toString() method, and after the call, the emitted event is logged via its toString()
  // method.
  //
  // If you wish to adjust the information in the log messages, do so via the toString() methods.
  // Financial transactions, passwords, and customer address data are examples of information which
  // should not be placed in the logs.
  //
  // If a command handler should not emit a log message, change logStart and logFinish to:
  // CommandLogLevel.NONE.
  @CommandHandler(logStart = CommandLogLevel.INFO, logFinish = CommandLogLevel.INFO)
  @Transactional
  @EventEmitter(selectorName = SmsalertEventConstants.SELECTOR_NAME, selectorValue = SmsalertEventConstants.POST_SMSALERT)
  public String sample(final SampleCommand sampleCommand) {

    final SampleJpaEntity entity = new SampleJpaEntity();
    entity.setIdentifier(sampleCommand.sample().getIdentifier());
    entity.setPayload(sampleCommand.sample().getPayload());
    this.sampleJpaEntityRepository.save(entity);

    return sampleCommand.sample().getIdentifier();
  }
}
