package tr.com.infumia.infumialib.plugin;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractJavaScheduler implements SchedulerAdapter {

  @NotNull
  private final ScheduledThreadPoolExecutor scheduler;

  @NotNull
  private final ErrorReportingExecutor schedulerWorkerPool;

  @NotNull
  private final ForkJoinPool worker;

  protected AbstractJavaScheduler() {
    this.scheduler = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder()
      .setDaemon(true)
      .setNameFormat("infumialib-scheduler")
      .build()
    );
    this.scheduler.setRemoveOnCancelPolicy(true);
    this.schedulerWorkerPool = new ErrorReportingExecutor(Executors.newCachedThreadPool(new ThreadFactoryBuilder()
      .setDaemon(true)
      .setNameFormat("infumialib-scheduler-worker-%d")
      .build()
    ));
    this.worker = new ForkJoinPool(32, ForkJoinPool.defaultForkJoinWorkerThreadFactory, (t, e) -> e.printStackTrace(), false);
  }

  @NotNull
  @Override
  public Executor async() {
    return this.worker;
  }

  @NotNull
  @Override
  public SchedulerTask asyncLater(@NotNull final Runnable task, final long delay, @NotNull final TimeUnit unit) {
    final var future = this.scheduler.schedule(() -> this.schedulerWorkerPool.execute(task), delay, unit);
    return () -> future.cancel(false);
  }

  @NotNull
  @Override
  public SchedulerTask asyncRepeating(@NotNull final Runnable task, final long interval, @NotNull final TimeUnit unit) {
    final var future = this.scheduler.scheduleAtFixedRate(() -> this.schedulerWorkerPool.execute(task), interval, interval, unit);
    return () -> future.cancel(false);
  }

  @Override
  public void shutdownExecutor() {
    this.schedulerWorkerPool.delegate.shutdown();
    try {
      this.schedulerWorkerPool.delegate.awaitTermination(1, TimeUnit.MINUTES);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void shutdownScheduler() {
    this.scheduler.shutdown();
    try {
      this.scheduler.awaitTermination(1, TimeUnit.MINUTES);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  private static final class ErrorReportingExecutor implements Executor {

    @NotNull
    private final ExecutorService delegate;

    @Override
    public void execute(@NotNull final Runnable command) {
      this.delegate.execute(new ErrorReportingRunnable(command));
    }
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  private static final class ErrorReportingRunnable implements Runnable {

    @NotNull
    private final Runnable delegate;

    @Override
    public void run() {
      try {
        this.delegate.run();
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
  }
}