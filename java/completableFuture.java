private void buildTransitRequestHistoryDto(List<TransitRequestHistoryDto> transitRequestHistoryDtoList)
        throws ExecutionException, InterruptedException {
        CompletableFuture<Map<String, AbstractWaypoint>> waypointTask = createWaypointTask(transitRequestHistoryDtoList)
            .handle(Utils.handleException(log, "'AbstractWaypoint' 聚合查询 'waypoint' 失败", new HashMap<>()));
        CompletableFuture<Map<String, Robot>> robotTask = createRobotTask(transitRequestHistoryDtoList)
            .handle(Utils.handleException(log, "'Robot' 聚合查询 'Robot' 失败", new HashMap<>()));
        CompletableFuture<Map<String, Hive>> hiveTask = createHiveTask(transitRequestHistoryDtoList)
            .handle(Utils.handleException(log, "'Hive' 聚合查询 'Hive' 失败", new HashMap<>()));
        CompletableFuture.allOf(waypointTask, robotTask, hiveTask).join();
        Map<String, AbstractWaypoint> waypointMap = waypointTask.get();
        Map<String, Robot> robotMap = robotTask.get();
        Map<String, Hive> hiveMap = hiveTask.get();
        transitRequestHistoryDtoList.forEach(a -> {
            Utils.setValue(a, TransitRequestHistoryDto::getStartWaypointId,
                TransitRequestHistoryDto::setStartWaypoint, waypointMap);
            Utils.setValue(a, TransitRequestHistoryDto::getReturnWaypointId,
                TransitRequestHistoryDto::setReturnWaypoint, waypointMap);
            Utils.setValue(a, TransitRequestHistoryDto::getEndWaypointId,
                TransitRequestHistoryDto::setEndWaypoint, waypointMap);
            Utils.setValue(a, TransitRequestHistoryDto::getRobotId,
                TransitRequestHistoryDto::setRobot, robotMap);
            Utils.setValue(a, TransitRequestHistoryDto::getHiveId,
                TransitRequestHistoryDto::setHive, hiveMap);
        });
    }

    private CompletableFuture<Map<String, Hive>> createHiveTask(
        List<TransitRequestHistoryDto> transitRequestHistoryDtoList) {
        List<String> hiveIdList =
            Utils.extractIds(transitRequestHistoryDtoList, TransitRequestHistoryDto::getHiveId);
        return CompletableFuture.supplyAsync(() -> {
            if (CollectionUtils.isEmpty(hiveIdList)) {
                return new HashMap<>();
            }
            return Utils.toMap(deviceRepository.findHiveList(hiveIdList), Hive::getId);
        }, executor);
    }

    private CompletableFuture<Map<String, Robot>> createRobotTask(
        List<TransitRequestHistoryDto> transitRequestHistoryDtoList) {
        List<String> robotIdList =
            Utils.extractIds(transitRequestHistoryDtoList, TransitRequestHistoryDto::getRobotId);
        return CompletableFuture.supplyAsync(() -> {
            if (CollectionUtils.isEmpty(robotIdList)) {
                return new HashMap<>();
            }
            return Utils.toMap(deviceRepository.findRobotList(robotIdList), Robot::getId);
        }, executor);
    }

    private CompletableFuture<Map<String, AbstractWaypoint>> createWaypointTask(
        List<TransitRequestHistoryDto> transitRequestHistoryDtoList) {
        List<String> startWaypointIdList =
            Utils.extractIds(transitRequestHistoryDtoList, TransitRequestHistoryDto::getStartWaypointId);
        List<String> returnWaypointIdList =
            Utils.extractIds(transitRequestHistoryDtoList, TransitRequestHistoryDto::getReturnWaypointId);
        List<String> endWaypointIdList =
            Utils.extractIds(transitRequestHistoryDtoList, TransitRequestHistoryDto::getEndWaypointId);
        List<String> collect = Stream.of(startWaypointIdList, returnWaypointIdList, endWaypointIdList)
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
        return findAggregateWaypointList(collect, executor);
    }

    private CompletableFuture<Map<String, AbstractWaypoint>> findAggregateWaypointList(List<String> waypointIds,
        Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            if (CollectionUtils.isEmpty(waypointIds)) {
                return new HashMap<>();
            }
            return Utils.toMap(mapRepository.findWaypointsByIds(waypointIds), AbstractWaypoint::getId);
        }, executor);
    }